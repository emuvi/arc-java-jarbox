package pin.jarbox.wzd;

import java.io.File;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Objects;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class WzdFile {

  public static String fixSeparators(String path) {
    if (path == null) {
      return null;
    }
    if (path.contains("\\") && File.separator.equals("/")) {
      path = path.replaceAll("\\", "/");
    } else if (path.contains("/") && File.separator.equals("\\")) {
      path = path.replaceAll("/", "\\");
    }
    return path;
  }

  public static String sum(String path, String child) {
    String result = child;
    if (path != null) {
      if (path.endsWith(File.separator) || child.startsWith(File.separator)) {
        result = path + child;
      } else {
        result = path + File.separator + child;
      }
    }
    return result;
  }

  public static String sum(String path, String... childs) {
    String result = path;
    if (childs != null) {
      for (String filho : childs) {
        result = sum(result, filho);
      }
    }
    return result;
  }

  public static File sum(File path, String... childs) {
    File result = path;
    if (result != null) {
      if (childs != null) {
        for (String filho : childs) {
          result = new File(result, filho);
        }
      }
    }
    return result;
  }

  public static File getParent(File path, String withName) {
    File result = null;
    if (path != null) {
      File actual = path.getParentFile();
      while (!withName.equals(actual.getName())) {
        actual = actual.getParentFile();
        if (actual == null) {
          break;
        }
      }
      result = actual;
    }
    return result;
  }

  public static String getParent(String path) {
    if (path.contains(File.separator)) {
      return path.substring(0, path.lastIndexOf(File.separator));
    } else {
      return path;
    }
  }

  public static File getRoot(String withName, File fromPath) {
    if (fromPath == null) {
      return null;
    }
    File result = fromPath.getParentFile();
    while (result != null && !Objects.equals(withName, result.getName())) {
      result = result.getParentFile();
    }
    return result;
  }

  public static String getName(String path) {
    if (path == null) {
      return null;
    }
    final int sep = path.lastIndexOf(File.separator);
    if (sep == -1) {
      return path;
    } else {
      return path.substring(sep + 1);
    }
  }

  public static String getBaseName(String path) {
    if (path == null) {
      return null;
    }
    path = getName(path);
    final int dot = path.lastIndexOf(".");
    if (dot > -1) {
      return path.substring(0, dot);
    } else {
      return path;
    }
  }

  public static String getExtension(String path) {
    if (path == null) {
      return null;
    }
    final int dot = path.lastIndexOf(".");
    if (dot > -1) {
      return path.substring(dot);
    } else {
      return "";
    }
  }

  public static String putOnBaseName(String path, String chars) {
    String result = chars;
    if (path != null) {
      if (path.contains(".")) {
        result = path.substring(0, path.lastIndexOf(".")) + chars + path.substring(path
            .lastIndexOf("."));
      } else {
        result = path + chars;
      }
    }
    return result;
  }

  public static File notOverride(File path) {
    if (!path.exists()) {
      return path;
    } else {
      File result = null;
      int tent = 2;
      do {
        result = new File(putOnBaseName(path.getAbsolutePath(), " (" + tent + ")"));
        tent++;
      } while (result.exists());
      return result;
    }
  }

  public static JFileChooser chooser(String description, String... extensions) {
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Select");
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setAcceptAllFileFilterUsed(extensions == null);
    FileFilter filter = new FileNameExtensionFilter(description, extensions);
    chooser.setFileFilter(filter);
    return chooser;
  }

  public static File open() {
    return open(null);
  }

  public static File open(File selected) {
    return open(selected, null);
  }

  public static File open(String description, String... extensions) {
    return open(null, description, extensions);
  }

  public static File open(File selected, String description, String... extensions) {
    File result = null;
    if (WzdDesk.started) {
      JFileChooser chooser = new JFileChooser();
      chooser.setDialogTitle("Select File or Directory");
      chooser.setMultiSelectionEnabled(false);
      chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      chooser.setAcceptAllFileFilterUsed(extensions == null);
      if (selected != null) {
        chooser.setSelectedFile(selected);
      }
      if (description != null & extensions != null) {
        FileFilter filter = new FileNameExtensionFilter(description, extensions);
        chooser.setFileFilter(filter);
      }
      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        result = chooser.getSelectedFile();
      }
    } else {
      result = selectFileTerminal(FileTerminalAction.OPEN, FileTerminalNature.FILE_OR_DIR,
          selected, description, extensions);
    }
    return result;
  }

  public static File openFile() {
    return openFile(null);
  }

  public static File openFile(File selected) {
    return openFile(selected, null);
  }

  public static File openFile(String description, String... extensions) {
    return openFile(null, description, extensions);
  }

  public static File openFile(File selected, String description, String... extensions) {
    File result = null;
    if (WzdDesk.started) {
      JFileChooser chooser = new JFileChooser();
      chooser.setDialogTitle("Select File");
      chooser.setMultiSelectionEnabled(false);
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      chooser.setAcceptAllFileFilterUsed(extensions == null);
      if (selected != null) {
        chooser.setSelectedFile(selected);
      }
      if (description != null & extensions != null) {
        FileFilter filter = new FileNameExtensionFilter(description, extensions);
        chooser.setFileFilter(filter);
      }
      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        result = chooser.getSelectedFile();
      }
    } else {
      result = selectFileTerminal(FileTerminalAction.OPEN, FileTerminalNature.FILE,
          selected, description, extensions);
    }
    return result;
  }

  public static File openDir() {
    return openDir(null);
  }

  public static File openDir(File selected) {
    return openDir(selected, null);
  }

  public static File openDir(String description, String... extensions) {
    return openDir(null, description, extensions);
  }

  public static File openDir(File selected, String description, String... extensions) {
    File result = null;
    if (WzdDesk.started) {
      JFileChooser chooser = new JFileChooser();
      chooser.setDialogTitle("Select Directory");
      chooser.setMultiSelectionEnabled(false);
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      chooser.setAcceptAllFileFilterUsed(extensions == null);
      if (selected != null) {
        chooser.setSelectedFile(selected);
      }
      if (description != null & extensions != null) {
        FileFilter filter = new FileNameExtensionFilter(description, extensions);
        chooser.setFileFilter(filter);
      }
      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        result = chooser.getSelectedFile();
      }
    } else {
      result = selectFileTerminal(FileTerminalAction.OPEN, FileTerminalNature.DIR,
          selected, description, extensions);
    }
    return result;
  }

  public static File[] openMany() {
    return openMany(null);
  }

  public static File[] openMany(File[] selecteds) {
    return openMany(selecteds, null);
  }

  public static File[] openMany(String description, String... extensions) {
    return openMany(null, description, extensions);
  }

  public static File[] openMany(File[] selecteds, String description,
      String... extensions) {
    File[] result = null;
    if (WzdDesk.started) {
      JFileChooser chooser = new JFileChooser();
      chooser.setDialogTitle("Select Many Files or Directories");
      chooser.setMultiSelectionEnabled(true);
      chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      chooser.setAcceptAllFileFilterUsed(extensions == null);
      if (selecteds != null) {
        chooser.setSelectedFiles(selecteds);
      }
      if (description != null & extensions != null) {
        FileFilter filter = new FileNameExtensionFilter(description, extensions);
        chooser.setFileFilter(filter);
      }
      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        result = chooser.getSelectedFiles();
      }
    } else {
      result = selectFileTerminalMany(true, FileTerminalAction.OPEN,
          FileTerminalNature.FILE_OR_DIR, selecteds, description, extensions);
    }
    return result;
  }

  public static File[] openFileMany() {
    return openFileMany(null);
  }

  public static File[] openFileMany(File[] selecteds) {
    return openFileMany(selecteds, null);
  }

  public static File[] openFileMany(String description, String... extensions) {
    return openFileMany(null, description, extensions);
  }

  public static File[] openFileMany(File[] selecteds, String description,
      String... extensions) {
    File[] result = null;
    if (WzdDesk.started) {
      JFileChooser chooser = new JFileChooser();
      chooser.setDialogTitle("Select Many Files");
      chooser.setMultiSelectionEnabled(true);
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      chooser.setAcceptAllFileFilterUsed(extensions == null);
      if (selecteds != null) {
        chooser.setSelectedFiles(selecteds);
      }
      if (description != null & extensions != null) {
        FileFilter filter = new FileNameExtensionFilter(description, extensions);
        chooser.setFileFilter(filter);
      }
      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        result = chooser.getSelectedFiles();
      }
    } else {
      result = selectFileTerminalMany(true, FileTerminalAction.OPEN,
          FileTerminalNature.FILE, selecteds, description, extensions);
    }
    return result;
  }

  public static File[] openDirMany() {
    return openDirMany(null);
  }

  public static File[] openDirMany(File[] selecteds) {
    return openDirMany(selecteds, null);
  }

  public static File[] openDirMany(String description, String... extensions) {
    return openDirMany(null, description, extensions);
  }

  public static File[] openDirMany(File[] selecteds, String description,
      String... extensions) {
    File[] result = null;
    if (WzdDesk.started) {
      JFileChooser chooser = new JFileChooser();
      chooser.setDialogTitle("Select Many Directories");
      chooser.setMultiSelectionEnabled(true);
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      chooser.setAcceptAllFileFilterUsed(extensions == null);
      if (selecteds != null) {
        chooser.setSelectedFiles(selecteds);
      }
      if (description != null & extensions != null) {
        FileFilter filter = new FileNameExtensionFilter(description, extensions);
        chooser.setFileFilter(filter);
      }
      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        result = chooser.getSelectedFiles();
      }
    } else {
      result = selectFileTerminalMany(true, FileTerminalAction.OPEN,
          FileTerminalNature.DIR, selecteds, description, extensions);
    }
    return result;
  }

  public static File save() {
    return save(null);
  }

  public static File save(File selected) {
    return save(selected, null);
  }

  public static File save(String description, String... extensions) {
    return save(null, description, extensions);
  }

  public static File save(File selected, String description, String... extensions) {
    File result = null;
    if (WzdDesk.started) {
      JFileChooser chooser = new JFileChooser();
      chooser.setDialogTitle("Save File or Directory");
      chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      chooser.setAcceptAllFileFilterUsed(extensions == null);
      if (selected != null) {
        chooser.setSelectedFile(selected);
      }
      if (description != null & extensions != null) {
        FileFilter filter = new FileNameExtensionFilter(description, extensions);
        chooser.setFileFilter(filter);
      }
      if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
        result = chooser.getSelectedFile();
        String ext = getExtension(result.getAbsolutePath());
        if (extensions != null) {
          if (!WzdArray.has(ext, extensions)) {
            result = new File(result.getAbsolutePath() + "." + extensions[0]);
          }
        }
      }
    } else {
      result = selectFileTerminal(FileTerminalAction.SAVE, FileTerminalNature.FILE_OR_DIR,
          selected, description, extensions);
    }
    return result;
  }

  public static File saveFile() {
    return saveFile(null);
  }

  public static File saveFile(File selected) {
    return saveFile(selected, null);
  }

  public static File saveFile(String description, String... extensions) {
    return saveFile(null, description, extensions);
  }

  public static File saveFile(File selected, String description, String... extensions) {
    File result = null;
    if (WzdDesk.started) {
      JFileChooser chooser = new JFileChooser();
      chooser.setDialogTitle("Save File");
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      chooser.setAcceptAllFileFilterUsed(extensions == null);
      if (selected != null) {
        chooser.setSelectedFile(selected);
      }
      if (description != null & extensions != null) {
        FileFilter filter = new FileNameExtensionFilter(description, extensions);
        chooser.setFileFilter(filter);
      }
      if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
        result = chooser.getSelectedFile();
        if (extensions != null) {
          if (extensions.length > 0) {
            boolean mustInclude = true;
            if (result.getName().contains(".")) {
              String ext = getExtension(result.getName());
              if (WzdArray.has(ext, extensions)) {
                mustInclude = false;
              }
            }
            if (mustInclude) {
              result = new File(result.getAbsolutePath() + "." + extensions[0]);
            }
          }
        }
      }
    } else {
      result = selectFileTerminal(FileTerminalAction.SAVE, FileTerminalNature.FILE,
          selected, description, extensions);
    }
    return result;
  }

  public static File saveDir() {
    return saveDir(null);
  }

  public static File saveDir(File selected) {
    return saveDir(selected, null);
  }

  public static File saveDir(String description, String... extensions) {
    return saveDir(null, description, extensions);
  }

  public static File saveDir(File selected, String description, String... extensions) {
    File result = null;
    if (WzdDesk.started) {
      JFileChooser chooser = new JFileChooser();
      chooser.setDialogTitle("Save Directory");
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      chooser.setAcceptAllFileFilterUsed(extensions == null);
      if (selected != null) {
        chooser.setSelectedFile(selected);
      }
      if (description != null & extensions != null) {
        FileFilter filter = new FileNameExtensionFilter(description, extensions);
        chooser.setFileFilter(filter);
      }
      if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
        result = chooser.getSelectedFile();
        String ext = getExtension(result.getAbsolutePath());
        if (extensions != null) {
          if (!WzdArray.has(ext, extensions)) {
            result = new File(result.getAbsolutePath() + "." + extensions[0]);
          }
        }
      }
    } else {
      result = selectFileTerminal(FileTerminalAction.SAVE, FileTerminalNature.DIR,
          selected, description, extensions);
    }
    return result;
  }

  private static enum FileTerminalAction {
    OPEN, SAVE
  }

  private static enum FileTerminalNature {
    FILE, DIR, FILE_OR_DIR
  }

  private static File selectFileTerminal(FileTerminalAction action,
      FileTerminalNature nature, File selected, String description,
      String... extensions) {
    return selectFileTerminalMany(false, action, nature, new File[] {selected},
        description, extensions)[0];
  }

  private static File[] selectFileTerminalMany(boolean askForMany,
      FileTerminalAction action, FileTerminalNature nature, File[] selecteds,
      String description, String... extensions) {
    throw new UnsupportedOperationException(
        "Select file(s) from terminal is not yet implemmented.");
  }

  public static String bytesToHex(byte[] bytes) {
    StringBuilder hexString = new StringBuilder(2 * bytes.length);
    for (int i = 0; i < bytes.length; i++) {
      String hex = Integer.toHexString(0xff & bytes[i]);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }

  public static String checkSHA256(File file) throws Exception {
    return checkSHA256(Files.readAllBytes(file.toPath()));
  }

  public static String checkSHA256(byte[] bytes) throws Exception {
    return bytesToHex(MessageDigest.getInstance("SHA-256").digest(bytes));
  }

}
