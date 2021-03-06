public class MyFinalizeTest {

  private boolean resourcesInUse;

  public synchronized void allocateResources() {
    performAllocate();
    resourcesInUse = true;
  }

  public synchronized void releaseResources() {
    performRelease();
    resourcesInUse = false;
  }

  /**
   *  If we're still holding resources, release them now
   */
  protected synchronized void finalize() throws Throwable {
    if (resourcesInUse) {
      releaseResources();
    }
  }

  //  Allocate resources here
  protected void performAllocate() {
  }

  //  Release resources here
  protected void performRelease() {
  }

}
