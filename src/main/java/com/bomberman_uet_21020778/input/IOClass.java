package com.bomberman_uet_21020778.input;

import java.io.*;
import java.util.Scanner;

public class IOClass {
  public static String path = "res\\\\highscore.txt";

  public static String Read() {
    String str = null;
    File file = new File(path);

    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNext()) {
        str = sc.nextLine();
      }
    } catch (Exception e) {
      e.getMessage();
    }
    return str;
  }
  public static void write(int point) throws IOException {
    File file = new File(path);

    try (FileWriter fw = new FileWriter(file, false)) {
      BufferedWriter bf = new BufferedWriter(fw);
      PrintWriter pw = new PrintWriter(bf);
      pw.println(point);
    }
    catch (Exception ignored) {

    }
  }
}
