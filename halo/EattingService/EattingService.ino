#include <Wire.h>
#include <RTClib.h>
#include <Servo.h>

// Khai báo các đối tượng
RTC_DS3231 rtc;
Servo servo;

// Chân điều khiển Servo
int servoPin = 9;

// Cài đặt thời gian cho các lần cho cá ăn (ví dụ: 8:00 AM và 6:00 PM)
int feedingHour1 = 8;    // 8:00 AM
int feedingMinute1 = 0;
int feedingHour2 = 18;   // 6:00 PM
int feedingMinute2 = 0;

void setup() {
  // Khởi tạo servo
  servo.attach(servoPin);
  
  // Khởi tạo module RTC
  if (!rtc.begin()) {
    Serial.println("Không tìm thấy RTC!");
    while (1);
  }

  // Nếu bạn muốn cài đặt lại thời gian cho RTC, hãy mở dòng dưới:
  // rtc.adjust(DateTime(F(__DATE__), F(__TIME__)));

  // In thời gian hiện tại ra màn hình Serial
  Serial.begin(9600);
}

void loop() {
  // Lấy thời gian hiện tại từ RTC
  DateTime now = rtc.now();

  // Kiểm tra nếu thời gian hiện tại khớp với thời gian cho cá ăn
  if (now.hour() == feedingHour1 && now.minute() == feedingMinute1) {
    feedFish();
  } else if (now.hour() == feedingHour2 && now.minute() == feedingMinute2) {
    feedFish();
  }

  delay(60000); // Delay 1 phút để không lặp lại quá nhanh
}

void feedFish() {
  // Quay servo để đổ thức ăn vào bể cá
  servo.write(90);    // Quay servo 90 độ (hoặc tùy theo cơ cấu của bạn)
  delay(1000);        // Giữ servo ở vị trí trong 1 giây
  servo.write(0);     // Quay lại vị trí ban đầu
}
