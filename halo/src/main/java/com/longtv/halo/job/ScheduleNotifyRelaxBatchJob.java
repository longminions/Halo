package com.longtv.halo.job;

import com.longtv.halo.entity.*;
import com.longtv.halo.service.*;
import jakarta.persistence.*;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.*;
import org.springframework.batch.core.repository.*;
import org.springframework.batch.core.step.builder.*;
import org.springframework.batch.core.step.tasklet.*;
import org.springframework.batch.item.*;
import org.springframework.batch.item.database.builder.*;
import org.springframework.batch.repeat.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.transaction.*;

@Configuration
public class ScheduleNotifyRelaxBatchJob {
	
	@Autowired
	private final EntityManagerFactory entityManagerFactory;
	
	@Autowired
	private NotifyKafkaProducer notifyKafkaProducer;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Autowired
	public ScheduleNotifyRelaxBatchJob(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
	
	// ItemReader: Đọc các User cần gửi thông báo thư giãn
	// Sử dụng JpaPagingItemReader để đọc dữ liệu từ database theo trang
	@Bean
	public ItemReader<User> relaxNotificationReader() {
		System.out.println("--- Setting up RelaxNotificationReader ---");
		
		// JPQL query để lấy User. Cần điều chỉnh điều kiện WHERE theo logic nghiệp vụ của bạn.
		// Ví dụ: lấy User mà lần cuối gửi thông báo đã lâu, hoặc theo lịch trình cụ thể.
		String jpqlQuery = "SELECT u FROM User u WHERE u.needsRelaxNotification = true"; // Giả định có thuộc tính needsRelaxNotification
		
		// Sử dụng Builder để cấu hình JpaPagingItemReader
		return new JpaPagingItemReaderBuilder<User>().name(
						"relaxNotificationReader") // Tên Reader (quan trọng cho trạng thái Batch)
				       .entityManagerFactory(entityManagerFactory) // Sử dụng EntityManagerFactory đã inject
				       .queryString(jpqlQuery) // JPQL query
				       .pageSize(100) // Kích thước trang đọc (cũng là kích thước chunk mặc định nếu không cấu hình riêng)
				       .build();
	}
	
	// ItemProcessor (Tùy chọn): Xử lý User và tạo thông báo
	// Chuyển đổi Entity User thành một String thông báo đơn giản
	@Bean
	public ItemProcessor<User, String> relaxNotificationProcessor() {
		System.out.println("--- Setting up RelaxNotificationProcessor ---");
		return user -> {
			// Logic tạo nội dung thông báo.
			// Ví dụ: "Hey [FirstName], take a moment to relax!"
			String notificationMessage = String.format(
					"Sending relax notification to User ID: %d (%s %s) - Message: Hey %s, take a moment to relax!",
					user.getId(), user.getFirstName(), user.getLastName(), user.getFirstName());
			System.out.println("Processing User for notification: " + user.getEmail()); // Log để theo dõi
			return notificationMessage; // Trả về thông báo đã tạo
		};
	}
	
	// ItemWriter: Ghi thông báo (ví dụ: gửi email, ghi vào log, gửi Kafka)
	// Ví dụ này chỉ ghi thông báo ra console/log
	@Bean
	public ItemWriter<String> relaxNotificationWriter() {
		System.out.println("--- Setting up RelaxNotificationWriter ---");
		return messages -> {
			// Logic gửi thông báo thực tế (email, SMS, push notification, Kafka, etc.)
			// Trong ví dụ này, chúng ta chỉ in ra console
			for (String message : messages) {
				System.out.println(message);
				notifyKafkaProducer.send(message);
			}
			System.out.println("--- Finished Writing Chunk ---");
		};
	}
	
	// === Định nghĩa Step ===
	
	// Step: Kết hợp Reader, Processor, Writer và cấu hình chunk
	@Bean
	public Step notifyRelaxStep() {
		System.out.println("--- Building notifyRelaxStep ---");
		return new StepBuilder("notifyRelaxStep", jobRepository) // Tên Step và JobRepository
				       // <InputItem (từ Reader), OutputItem (từ Processor)>chunk(chunkSize, transactionManager)
				       .<User, String> chunk(50, transactionManager) // Đọc User, xử lý ra String, theo chunk 50 mục
				       .reader(relaxNotificationReader()) // Gắn ItemReader
				       .processor(relaxNotificationProcessor()) // Gắn ItemProcessor
				       .writer(relaxNotificationWriter()) // Gắn ItemWriter
				       // Có thể thêm cấu hình xử lý lỗi, listener, v.v.
				       .build(); // Hoàn thành xây dựng Step
	}
	
	// =   == Định nghĩa Job ===

	// Job: Đại diện cho toàn bộ quy trình Batch
		@Bean
		public Job scheduleNotifyRelaxJob() {
			System.out.println("--- Building scheduleNotifyRelaxJob ---");
			
			return new JobBuilder("scheduleNotifyRelaxJob", jobRepository).start(notifyRelaxStep())
					       .build();
		}
}