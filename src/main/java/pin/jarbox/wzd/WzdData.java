package pin.jarbox.wzd;

import java.util.Base64;
import pin.jarbox.dat.Nature;

public class WzdData {

  public static Nature getNatureOfSQL(int jdbcType) {
    switch (jdbcType) {
      case 16:
        return Nature.Bool;
      case -7:
      case -6:
      case 5:
      case 4:
        return Nature.Int;
      case -5:
        return Nature.Long;
      case 6:
      case 7:
        return Nature.Float;
      case 8:
      case 2:
      case 3:
        return Nature.Double;
      case 1:
      case -15:
        return Nature.Char;
      case 12:
      case -1:
      case -9:
      case -16:
        return Nature.Chars;
      case 91:
        return Nature.Date;
      case 92:
      case 2013:
        return Nature.Time;
      case 93:
      case 2014:
        return Nature.Timestamp;
      case -2:
      case -3:
      case -4:
      case 2004:
      case 2005:
      case 2011:
      case 2009:
        return Nature.Bytes;
      default:
        return Nature.Undefined;
    }
  }



}
