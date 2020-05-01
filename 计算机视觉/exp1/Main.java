package exp1;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Main {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	public static void main(String[] args) {
		String imagePath = ".\\images\\test.jpg";
		// imaread(path,[flag])
		Mat srcImage1 = Imgcodecs.imread(imagePath, 1);
		// �ԻҶ�ģʽ����
		Mat srcImage2 = Imgcodecs.imread(imagePath, 0);
		Mat dstImage1 = srcImage1.clone();
		Mat dstImage2 =srcImage1.clone();
		Mat dstImage3 = srcImage1.clone();

		HighGui.imshow("ԭͼ", srcImage1);
		HighGui.imshow("��ͼ", srcImage2);

		// ֱ��ͼ���⻯
		Imgproc.equalizeHist(srcImage2, dstImage1);
		HighGui.imshow("ֱ��ͼ����", dstImage1);

		// ƽ���˲���Ϊ���Ժͷ�����
		// �����˲�������ֵ�˲�
		/*
		 * InputArray src: ����ͼ�񣬿�����Mat���ͣ�ͼ�������CV_8U��CV_16U��CV_16S��CV_32F�Լ�CV_64F���е�ĳһ����
		 * OutputArray dst: ���ͼ����Ⱥ�����������ͼ��һ�� Size ksize: �˲�ģ��kernel�ĳߴ磬һ��ʹ��Size(w,
		 * h)��ָ������Size(3,3) Point anchor=Point(-1, -1):
		 * ������˼��ê�㣬Ҳ���Ǵ��������λ��kernel��ʲôλ�ã�Ĭ��ֵΪ(-1, -1)��λ��kernel���ĵ㣬���û��������Ҫ����Ҫ���� int
		 * borderType=BORDER_DEFAULT: �����ƶ�ͼ���ⲿ���ص�ĳ�ֱ߽�ģʽ����Ĭ��ֵBORDER_DEFAULT
		 */
		Imgproc.blur(srcImage1, dstImage2, new Size(5, 5), new Point(-1, -1));

		HighGui.imshow("��ֵ�˲�", dstImage2);

		// �������˲�������ֵ�˲�
		/*
		 * InputArray src:
		 * ����ͼ��ͼ��Ϊ1��3��4ͨ����ͼ�񣬵�ģ��ߴ�Ϊ3��5ʱ��ͼ�����ֻ��ΪCV_8U��CV_16U��CV_32F�е�һ����������ڽϴ�׾��ߴ��ͼƬ��
		 * ͼ�����ֻ����CV_8U�� OutputArray dst:
		 * ���ͼ�񣬳ߴ������������ͼ��һ�£�����ʹ��Mat::Clone��ԭͼ��Ϊģ������ʼ�����ͼ��dst int ksize:
		 * �˲�ģ��ĳߴ��С�������Ǵ���1������
		 */
		Imgproc.medianBlur(srcImage1, dstImage3, 11);
		HighGui.imshow("��ֵ�˲�", dstImage3);

		HighGui.waitKey(0);

	}

}
