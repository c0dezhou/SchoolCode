package exp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Lexer {

	public static void main(String[] args) {
		String str=new String();//��ſ��ƿ����롢�ݴ�������
		File inFile,outFile = new File("./OUT");
		Scanner in = new Scanner(System.in);
		System.out.println("������ 1 ��ʾ���ļ���������,������ʾ�ӿ���̨���룺");
		if(in.nextLine().equals("1")) {
			inFile = new File("./IN");
			str = readFile(inFile);
		}else {
			str = in.nextLine();
			in.close();
		}
		//����ϴ����
		if(outFile.exists())
			outFile.delete();

		System.out.println("�ֱ��붨��: ��ʶ��:1������:2��������:3�������:4�����:5������:-1");
		int idNum = 0;//��ʶ�����
		HashMap<String,Integer> idMap=new HashMap<>();//���ű���ű�ʶ��
		ArrayList<String> list = preprocess(str);//�ַ�Ԥ����
		for (int i = 0; i < list.size(); i++) {
			//����Ǳ�ʶ��������Map
			if(identify(list.get(i)) == 1) {
				if(!idMap.containsKey(list.get(i))) {
					idMap.put(list.get(i), idNum++);
				}
				str="(1,"+idMap.get(list.get(i))+")";
			}
			else
				str="(" + identify(list.get(i)) + "," +list.get(i) + ")";
			
			System.out.println(str);
			writeFile(outFile,str);//׷��д
		}

	}

	// �����ֱ� ��ʶ��:1������:2��������:3�������:4�����:5 ������:-1
	private static int identify(String str) {

		String intPattern = "(0|[1-9]\\d*)"; // d. ʶ����������0 | (1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*
		String idPattern = "[A-Za-z]([A-Za-z]|\\d)*";// ��ʶ����<��ĸ>(<��ĸ>|<����>)*

		if (str.equals(";") || str.equals("(") || str.equals(")") || str.equals(",") || str.equals("."))
			return 5;
		else if (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("#") || str.equals("/")
				|| str.equals("=") || str.equals(">") || str.equals("<") || str.equals(">=") || str.equals("<=")
				|| str.equals(":=")|| str.equals(":"))
			return 4;
		else if (str.equals("if") || str.equals("then") || str.equals("else") || str.equals("while") || str.equals("do")
				|| str.equals("begin") || str.equals("end") || str.equals("read") || str.equals("write")|| str.equals("call")
				|| str.equals("var")|| str.equals("procedure"))
			return 3;
		else if (str.matches(intPattern))
			return 2;
		else if (str.matches(idPattern))
			return 1;
		else
			return -1;
	}

	// Ԥ�����ָ��ַ���
	private static ArrayList<String> preprocess(String str) {
		ArrayList<String> list = new ArrayList<>();
		String[] tmp = str.split("\\s");
		for (String t : tmp) {
			String tt = t.trim();
			if (!t.isEmpty()) {
				if (identify(tt) != -1)
					list.add(tt);
				else {
					// �����ָ�������
					list.add(tt.charAt(0)+"");
					//System.out.println("ttcharAt(0): "+tt);
					
					for(int i=1;i<tt.length();i++) {
						if(identify(tt.charAt(i)+"")==5) {//�ֽ��Ϊ����
							list.add(tt.charAt(i)+"");
							
						}else if(identify(tt.charAt(i)+"")==4) {//�����������
							String temp=list.get(list.size()-1);
							if(identify(temp.charAt(temp.length()-1)+"")==4)//a++++
								list.set(list.size()-1, list.get(list.size()-1)+tt.charAt(i));
							else 
								list.add(tt.charAt(i)+"");
							
						}else if(identify(tt.charAt(i)+"")==2){//����������
							if(identify(list.get(list.size()-1))==2)
								list.set(list.size()-1, list.get(list.size()-1)+tt.charAt(i));
							else 
								list.add(tt.charAt(i)+"");
							
						}else {//��ʶ��������
							if(identify(list.get(list.size()-1))==1||identify(list.get(list.size()-1))==3)
								list.set(list.size()-1, list.get(list.size()-1)+tt.charAt(i));
							else
								list.add(tt.charAt(i)+"");
						}
					}
				}
			}
		}
//		for (int i = 0; i < list.size(); i++)
//			System.out.print(list.get(i) + "  ");
//		System.out.println();
//		
		return list;
	}
	//���ļ�
	public static String readFile(File file) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = br.readLine()) != null) {
				result.append(System.lineSeparator() + s);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	//д�ļ�
	public static void writeFile(File file,String str) {
	     try {
	             FileWriter fw = new FileWriter(file,true);//׷��д
	             if(!file.exists())
	            	 file.createNewFile(); 
	             fw.append(str+"\n");
	             fw.flush();
	             fw.close();
	         } catch (IOException e) {
	             e.printStackTrace();
	         }
	}
}
