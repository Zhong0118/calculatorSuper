package com.example.superjjj.util;
import android.content.Context;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class RecordUtil {

    private static final String FILENAME = "records.txt";

    public static void saveRecord(Context context, String record) {
        try (FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_APPEND)) {
            // 将新的记录写入文件（以追加模式）
            fos.write(record.getBytes());
            // 写入换行符，以便将新记录追加在旧记录的后面
            fos.write("\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String loadRecord(Context context) {
        try (FileInputStream fis = context.openFileInput(FILENAME)) {
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void clear(Context context, String record) {
        try (FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)) {
            // 先清空文件内容
            fos.write(new byte[0]);
            // 将新的记录写入文件
            fos.write(record.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

