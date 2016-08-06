package me.darrillaga.prototype.viewer.childrenguardian.view;//package mobilesmil.utils;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Utilities for performing common color-related tasks.
 * @author Ryan Ware
 *
 */
public class ColorUtils {
  
  public static int getComplimentColor(Paint paint) {
    return getComplimentColor(paint.getColor());
  }
  
  /**
   * Returns the complimentary (opposite) color.
   * @param color int RGB color to return the compliment of
   * @return int RGB of compliment color
   */
  public static int getComplimentColor(int color) {
    // get existing colors
    int alpha = Color.alpha(color);
    int red = Color.red(color);
    int blue = Color.blue(color);
    int green = Color.green(color);
    
    // find compliments
    red = (~red) & 0xff;
    blue = (~blue) & 0xff;
    green = (~green) & 0xff;
    
    return Color.argb(alpha, red, green, blue);
  }

  public static int applyAlpha(int alpha, int color) {
    return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
  }
  
  /**
   * Converts an int RGB color representation into a hexadecimal {@link String}.
   * @param argbColor int RGB color
   * @return {@link String} hexadecimal color representation
   */
  public static String getHexStringForARGB(int argbColor) {
    String hexString = "#";
    hexString += ARGBToHex(Color.alpha(argbColor));
    hexString += ARGBToHex(Color.red(argbColor));
    hexString += ARGBToHex(Color.green(argbColor));
    hexString += ARGBToHex(Color.blue(argbColor));
    
    return hexString;
  }
  
  /**
   * Converts an int R, G, or B value into a hexadecimal {@link String}.
   * @param rgbVal int R, G, or B value
   * @return {@link String} hexadecimal value
   */
  private static String ARGBToHex(int rgbVal) {
    String hexReference = "0123456789ABCDEF";
    
    rgbVal = Math.max(0,rgbVal);
    rgbVal = Math.min(rgbVal,255);
    rgbVal = Math.round(rgbVal);
  
    return String.valueOf( hexReference.charAt((rgbVal-rgbVal%16)/16) + "" + hexReference.charAt(rgbVal%16) );
  }
}