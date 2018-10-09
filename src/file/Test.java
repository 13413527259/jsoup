package file;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		delAllFile("D:\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp2\\wtpwebapps\\oseman\\BGZLExcelZip");
	}

	// ɾ��ָ���ļ����������ļ�
	// param path �ļ�����������·��
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
		//ֻ�������5���ļ���
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
				delAllFile(path + "/" + list.get(i));// ��ɾ���ļ���������ļ�
				delFolder(path + "/" + list.get(i));// ��ɾ�����ļ���
				flag = true;
			}
		}
		return flag;
	}
	// ɾ���ļ���
	// param folderPath �ļ�����������·��

	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // ɾ����������������
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // ɾ�����ļ���
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
