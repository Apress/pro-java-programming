import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Downloader extends JPanel implements Runnable {

  protected URL downloadURL;
  protected InputStream inputStream;
  protected OutputStream outputStream;
  protected byte[] buffer;

  protected int fileSize;
  protected int bytesRead;

  protected JLabel urlLabel;
  protected JLabel sizeLabel;
  protected JLabel completeLabel;
  protected JProgressBar progressBar;

  public final static int BUFFER_SIZE = 1000;

  protected boolean stopped;
  protected boolean sleepScheduled;
  protected boolean suspended;

  public final static int SLEEP_TIME = 5 * 1000;  //  5 seconds

  protected Thread thisThread;

  public static ThreadGroup downloaderGroup = new ThreadGroup(
      "Download Threads");

  public static void main(String[] args) throws Exception {
    Downloader dl = null;
    if (args.length < 2) {
      System.out.println("You must specify the URL of the file " +
          "to download and "+
          "the name of the local file to " +
          "which its contents will be written.");
      System.exit(0);
    }
    URL url = new URL(args[0]);
    FileOutputStream fos = new FileOutputStream(args[1]);
    try {
      dl = new Downloader(url, fos);
    } catch (FileNotFoundException fnfe) {
      System.out.println("File '" + args[0] + "' does not exist");
      System.exit(0);
    }
    JFrame f = new JFrame();
    f.getContentPane().add(dl);
    f.setSize(600, 400);
    f.setVisible(true);
    dl.thisThread.start();
  }

  public Downloader(URL url, OutputStream os) throws IOException {
    downloadURL = url;
    outputStream = os;
    bytesRead = 0;
    URLConnection urlConnection = downloadURL.openConnection();
    fileSize = urlConnection.getContentLength();
    if (fileSize == -1) {
      throw new FileNotFoundException(url.toString());
    }
    inputStream = new BufferedInputStream(
        urlConnection.getInputStream());
    buffer = new byte[BUFFER_SIZE];
    thisThread = new Thread(downloaderGroup, this);
    buildLayout();

    stopped = false;
    sleepScheduled = false;
    suspended = false;
  }  

  protected void buildLayout() {
    JLabel label;
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 10, 5, 10);

    gbc.gridx = 0;
    label = new JLabel("URL:", JLabel.LEFT);
    add(label, gbc);

    label = new JLabel("Complete:", JLabel.LEFT);
    add(label, gbc);

    label = new JLabel("Downloaded:", JLabel.LEFT);
    add(label, gbc);

    gbc.gridx = 1;
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.weightx = 1;
    urlLabel = new JLabel(downloadURL.toString());
    add(urlLabel, gbc);

    progressBar = new JProgressBar(0, fileSize);
    progressBar.setStringPainted(true);
    add(progressBar, gbc);

    gbc.gridwidth = 1;
    completeLabel = new JLabel(Integer.toString(bytesRead));
    add(completeLabel, gbc);

    gbc.gridx = 2;
    gbc.weightx = 0;
    gbc.anchor = GridBagConstraints.EAST;
    label = new JLabel("Size:", JLabel.LEFT);
    add(label, gbc);

    gbc.gridx = 3;
    gbc.weightx = 1;
    sizeLabel = new JLabel(Integer.toString(fileSize));
    add(sizeLabel, gbc);
  }

  public void startDownload() {
    thisThread.start();
  }

  public synchronized void setSleepScheduled(boolean doSleep) {
    sleepScheduled = doSleep;
  }

  public synchronized boolean isSleepScheduled() {
    return sleepScheduled;
  }

  public synchronized void resumeDownload() {
    this.notify();
  }

  public synchronized void setStopped(boolean stop) {
    stopped = stop;
  }

  public synchronized boolean isStopped() {
    return stopped;
  }

  public void stopDownload() {
    thisThread.interrupt();
  }

  public void run() {
    performDownload();
  }

  public void performDownload() {
    int byteCount;
    Runnable progressUpdate = new Runnable() {
      public void run() {
        progressBar.setValue(bytesRead);
        completeLabel.setText(
            Integer.toString(
            bytesRead));
      }
    };
    while ((bytesRead < fileSize) && (!isStopped())) {
      try {
        if (isSleepScheduled()) {
          try {
            Thread.sleep(SLEEP_TIME);
            setSleepScheduled(false);
          }
          catch (InterruptedException ie) {
            setStopped(true);
            break;
          }
        }
        byteCount = inputStream.read(buffer);
        if (byteCount == -1) {
          setStopped(true);
          break;
        }
        else {
          outputStream.write(buffer, 0,
              byteCount);
          bytesRead += byteCount;
          SwingUtilities.invokeLater(
              progressUpdate);
        }
      } catch (IOException ioe) {
        setStopped(true);
        JOptionPane.showMessageDialog(this,
            ioe.getMessage(),
            "I/O Error",
            JOptionPane.ERROR_MESSAGE);
        break;
      }
      synchronized (this) {
        if (isSuspended()) {
          try {
            this.wait();
            setSuspended(false);
          }
          catch (InterruptedException ie) {
            setStopped(true);
            break;
          }
        }
      }
      if (Thread.interrupted()) {
        setStopped(true);
        break;
      }
    }
    try {
      outputStream.close();
      inputStream.close();
    } catch (IOException ioe) {};
  }

  public synchronized void setSuspended(boolean suspend) {
    suspended = suspend;
  }

  public synchronized boolean isSuspended() {
    return suspended;
  }

  public static void cancelAllAndWait() {
    int count = downloaderGroup.activeCount();
    Thread[] threads = new Thread[count];
    count = downloaderGroup.enumerate(threads);
    downloaderGroup.interrupt();
    for (int i = 0; i < count; i++) {
      try {
        threads[i].join();
      } catch (InterruptedException ie) {};
    }
  }

}
