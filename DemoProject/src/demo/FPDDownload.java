package demo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.filechooser.FileSystemView;

public class FPDDownload {
	/**
	 * ������Url�������ļ�
	 * 
	 * @param urlStr
	 * @param fileName
	 * @param savePath
	 * @throws IOException
	 */
	public static void downLoadByUrl(String urlStr, String fileName, String savePath) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// ���ó�ʱ��Ϊ3��
		conn.setConnectTimeout(5 * 1000);
		// ��ֹ���γ���ץȡ������403����
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		// �õ�������
		InputStream inputStream = conn.getInputStream();
		// ��ȡ�Լ�����
		byte[] getData = readInputStream(inputStream);
		// �ļ�����λ��
		File saveDir = new File(savePath);
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}
		File file = new File(saveDir + File.separator + fileName);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(getData);
		if (fos != null) {
			fos.close();
		}
		if (inputStream != null) {
			inputStream.close();
		}
		System.out.println("info:" + url + " download success");

	}

	/**
	 * ���������л�ȡ�ֽ�����
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}

	public static void main(String[] args) {
		try {
			FileSystemView fsv = FileSystemView.getFileSystemView();
			File com = fsv.getHomeDirectory();
			System.out.println(com);
			downLoadByUrl(
					"https://www.mybiosource.com/images/tds/protocol_samples/MBS700_Antibody_Set_Sandwich_ELISA_Protocol.pdf",
					"ELISA.pdf", com.toString());
		} catch (Exception e) {
			System.out.println("info" + e.getMessage() + "download fail");
		}
	}
}
