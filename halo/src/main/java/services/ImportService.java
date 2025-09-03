package services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.longtv.halo.entity.User;
import com.longtv.halo.repository.UserRepository;

@Service
public class ImportService {
	
	@Autowired
	private UserRepository userRepository;
	
	public String importUsers(MultipartFile file) {
		List<User> users = new ArrayList<>();
		try(InputStream inputStream = file.getInputStream();
			Workbook workbook = new XSSFWorkbook(inputStream)) {
			Sheet sheet = workbook.getSheetAt(0);
			for(Row row : sheet) {
				if (row.getRowNum() == 0) continue; // Skip header row
				User user = new User();
				String firstName = String.valueOf(getCellValue(row, 0));
				String lastName = String.valueOf(getCellValue(row, 1));
				String mail = String.valueOf(getCellValue(row, 2));
				boolean needRelaxNotification = Boolean.parseBoolean(String.valueOf(getCellValue(row, 3)));
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setEmail(mail);
				user.setNeedsRelaxNotification(needRelaxNotification);
				users.add(user);
			}
			userRepository.saveAll(users);
			return "Users imported successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error importing users: " + e.getMessage();
		}
		
	}
	
	protected Object getCellValue (Row row, int cellIndex) {
		if (row.getCell(cellIndex) != null) {
			switch (row.getCell(cellIndex).getCellType()) {
				case STRING:
					return row.getCell(cellIndex).getStringCellValue();
				case NUMERIC:
					return String.valueOf(row.getCell(cellIndex).getNumericCellValue());
				case BOOLEAN:
					return String.valueOf(row.getCell(cellIndex).getBooleanCellValue());
				default:
					return "";
			}
		}
		return "";
	}

}
