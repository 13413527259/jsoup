package file;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		delAllFile("D:\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp2\\wtpwebapps\\oseman\\BGZLExcelZip");
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		List<String> list=Arrays.asList(tempList);
		Collections.sort(list);
		//只保留最近5个文件夹
		if (list.size()>5) {
			list=list.subList(0, list.size()-5);
		}
		File temp = null;
		for (int i = 0; i < list.size(); i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + list.get(i));
			} else {
				temp = new File(path + File.separator + list.get(i));
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + list.get(i));// 先删除文件夹里面的文件
				delFolder(path + "/" + list.get(i));// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
	// 删除文件夹
	// param folderPath 文件夹完整绝对路径

	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
