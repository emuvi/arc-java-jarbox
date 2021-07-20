package pin.jarbox.wzd;

import pin.jarbox.dat.DataNature;

public class WzdData {

  public static DataNature getNatureOfSQL(int jdbcType) {
    switch (jdbcType) {
      case 16:
        return DataNature.BOOL;
      case -7:
      case -6:
      case 5:
      case 4:
        return DataNature.INT;
      case -5:
        return DataNature.LONG;
      case 6:
      case 7:
        return DataNature.FLOAT;
      case 8:
      case 2:
      case 3:
        return DataNature.DOUBLE;
      case 1:
      case -15:
        return DataNature.CHAR;
      case 12:
      case -1:
      case -9:
      case -16:
        return DataNature.CHARS;
      case 91:
        return DataNature.DATE;
      case 92:
      case 2013:
        return DataNature.TIME;
      case 93:
      case 2014:
        return DataNature.TIMESTAMP;
      case -2:
      case -3:
      case -4:
      case 2004:
      case 2005:
      case 2011:
      case 2009:
        return DataNature.BYTES;
      default:
        throw new UnsupportedOperationException(
            "Could not identify the data nature of jdbc type: " + jdbcType);
    }
  }



}
