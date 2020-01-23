package com.safframework.netdiagnose.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @FileName: com.safframework.netdiagnose.utils.TCPUtils
 * @author: Tony Shen
 * @date: 2019-10-09 12:02
 * @version: V1.0 <描述当前版本功能>
 */
public class TCPUtils {

    public static String sendMsgBySocket(String cmd, String host, int port,boolean flag){

        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;

        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), 5000);
            socket.setSoTimeout(3000);

            os = socket.getOutputStream();

            byte[] byteArray = null;
            if (flag) {
                byteArray = hexStringToBytes(cmd);
            } else {
                byteArray = cmd.getBytes();
            }

            os.write(byteArray);
            os.flush();

            is = socket.getInputStream();
            byte[] bt = new byte[4096];
            int readed = is.read(bt);

            if(readed > 0){
                byte[] newBytes = new byte[readed];
                System.arraycopy(bt,0,newBytes,0,readed);
                return new String(newBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtilsKt.closeQuietly(os);
            IOUtilsKt.closeQuietly(is);
            IOUtilsKt.closeQuietly(socket);
        }

        return "";
    }

    /**
     * 把十六进制字符串还原成二进制字节数组
     * @param hexString
     * @return
     */
    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 字符串转换成为16进制(无需Unicode编码)
     *
     * @param str
     * @return
     */
    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }
}
