package ueditor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import sun.misc.BASE64Decoder;

public class Uploader
{
  private static final int MAX_SIZE = 512000;
  private String url = "";

  private String fileName = "";

  private String state = "";

  private String type = "";

  private String originalName = "";

  private String size = "";

  private HttpServletRequest request = null;
  private String title = "";

  private String savePath = "upload";

  private String[] allowFiles = { ".rar", ".doc", ".docx", ".zip", ".pdf", 
    ".txt", ".swf", ".wmv", ".gif", ".png", ".jpg", ".jpeg", ".bmp" };

  private long maxSize = 0L;

  private HashMap<String, String> errorInfo = new HashMap();
  private Map<String, String> params = null;

  private InputStream inputStream = null;

  public static final String ENCODEING = System.getProperties().getProperty(
    "file.encoding");

  public Uploader(HttpServletRequest request)
  {
    this.request = request;
    this.params = new HashMap();

    setMaxSize(512000L);

    HashMap tmp = this.errorInfo;
    tmp.put("SUCCESS", "SUCCESS");

    tmp.put("NOFILE", 
      "\\u672a\\u5305\\u542b\\u6587\\u4ef6\\u4e0a\\u4f20\\u57df");

    tmp.put("TYPE", 
      "\\u4e0d\\u5141\\u8bb8\\u7684\\u6587\\u4ef6\\u683c\\u5f0f");

    tmp.put("SIZE", 
      "\\u6587\\u4ef6\\u5927\\u5c0f\\u8d85\\u51fa\\u9650\\u5236");

    tmp.put("ENTYPE", "\\u8bf7\\u6c42\\u7c7b\\u578b\\u9519\\u8bef");

    tmp.put("REQUEST", "\\u4e0a\\u4f20\\u8bf7\\u6c42\\u5f02\\u5e38");

    tmp.put("FILE", "\\u672a\\u627e\\u5230\\u4e0a\\u4f20\\u6587\\u4ef6");

    tmp.put("IO", "IO\\u5f02\\u5e38");

    tmp.put("DIR", "\\u76ee\\u5f55\\u521b\\u5efa\\u5931\\u8d25");

    tmp.put("UNKNOWN", "\\u672a\\u77e5\\u9519\\u8bef");

    parseParams();
  }

  public void upload() throws Exception
  {
    boolean isMultipart = 
      ServletFileUpload.isMultipartContent(this.request);
    if (!(isMultipart)) {
      this.state = ((String)this.errorInfo.get("NOFILE"));
      return;
    }

    if (this.inputStream == null) {
      this.state = ((String)this.errorInfo.get("FILE"));
      return;
    }

    this.title = getParameter("pictitle");
    try
    {
      String savePath = getFolder(this.savePath);
	  System.out.println(savePath);

      if (!(checkFileType(this.originalName))) {
        this.state = ((String)this.errorInfo.get("TYPE"));
        return;
      }

      this.fileName = getName(this.originalName);
      this.type = getFileExt(this.fileName);
      this.url = savePath + "/" + this.fileName;

      FileOutputStream fos = new FileOutputStream(
        getPhysicalPath(this.url));
      BufferedInputStream bis = new BufferedInputStream(this.inputStream);
      byte[] buff = new byte[128];
      int count = -1;

      while ((count = bis.read(buff)) != -1)
      {
        fos.write(buff, 0, count);
      }

      bis.close();
      fos.close();

      this.state = ((String)this.errorInfo.get("SUCCESS"));
    } catch (Exception e) {
      e.printStackTrace();
      this.state = ((String)this.errorInfo.get("IO"));
    }
  }

  public void uploadBase64(String fieldName)
  {
    String savePath = getFolder(this.savePath);
    String base64Data = this.request.getParameter(fieldName);
    this.fileName = getName("test.png");
    this.url = savePath + "/" + this.fileName;
    BASE64Decoder decoder = new BASE64Decoder();
    try {
      File outFile = new File(getPhysicalPath(this.url));
      OutputStream ro = new FileOutputStream(outFile);
      byte[] b = decoder.decodeBuffer(base64Data);
      for (int i = 0; i < b.length; ++i) {
        if (b[i] < 0)
        {
          int tmp124_122 = i;
          byte[] tmp124_120 = b; tmp124_120[tmp124_122] = (byte)(tmp124_120[tmp124_122] + 256);
        }
      }
      ro.write(b);
      ro.flush();
      ro.close();
      this.state = ((String)this.errorInfo.get("SUCCESS"));
    } catch (Exception e) {
      this.state = ((String)this.errorInfo.get("IO"));
    }
  }

  public String getParameter(String name)
  {
    return ((String)this.params.get(name));
  }

  private boolean checkFileType(String fileName)
  {
    Iterator type = Arrays.asList(this.allowFiles).iterator();
    while (type.hasNext()) {
      String ext = (String)type.next();
      if (fileName.toLowerCase().endsWith(ext)) {
        return true;
      }
    }
    return false;
  }

  private String getFileExt(String fileName)
  {
    return fileName.substring(fileName.lastIndexOf("."));
  }

  private void parseParams()
  {
    DiskFileItemFactory dff = new DiskFileItemFactory();
    try {
      ServletFileUpload sfu = new ServletFileUpload(dff);
      sfu.setSizeMax(this.maxSize);
      sfu.setHeaderEncoding(ENCODEING);

      FileItemIterator fii = sfu.getItemIterator(this.request);

      while (fii.hasNext()) {
        FileItemStream item = fii.next();

        if (item.isFormField())
        {
          this.params.put(item.getFieldName(), 
            getParameterValue(item.openStream()));
        }
        else if (this.inputStream == null) {
          this.inputStream = item.openStream();
          this.originalName = item.getName();
          return;
        }

      }

    }
    catch (Exception e)
    {
      this.state = ((String)this.errorInfo.get("UNKNOWN"));
    }
  }

  private String getName(String fileName)
  {
    Random random = new Random();
    return (this.fileName = random.nextInt(10000) + 
      System.currentTimeMillis() + getFileExt(fileName));
  }

  private String getFolder(String path)
  {
    SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
    path = path + "/" + formater.format(new Date());
    File dir = new File(getPhysicalPath(path));
    if (!(dir.exists())) {
      try {
        dir.mkdirs();
      } catch (Exception e) {
        this.state = ((String)this.errorInfo.get("DIR"));
        return "";
      }
    }
    return path;
  }

  private String getPhysicalPath(String path)
  {
    String servletPath = this.request.getServletPath();
    String realPath = this.request.getSession().getServletContext()
      .getRealPath(servletPath);
    return new File(realPath).getParent() + "/" + path;
  }

  private String getParameterValue(InputStream in)
  {
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

    String result = "";
    String tmpString = null;
    try
    {
      while ((tmpString = reader.readLine()) != null) {
        result = result + tmpString;
      }
    }
    catch (Exception localException)
    {
    }

    return result;
  }

  public void setSavePath(String savePath)
  {
    this.savePath = savePath;
  }

  public void setAllowFiles(String[] allowFiles) {
    this.allowFiles = allowFiles;
  }

  public void setMaxSize(long size) {
    this.maxSize = (size * 1024L);
  }

  public String getSize() {
    return this.size;
  }

  public String getUrl() {
    return this.url;
  }

  public String getFileName() {
    return this.fileName;
  }

  public String getState() {
    return this.state;
  }

  public String getTitle() {
    return this.title;
  }

  public String getType() {
    return this.type;
  }

  public String getOriginalName() {
    return this.originalName;
  }
}