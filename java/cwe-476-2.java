public class CWE476_NULL_Pointer_Dereference__int_array_01 extends AbstractTestCase
{
  public void bad() throws Throwable
  {
    int [] data;
    data = null;
    IO.writeLine("" + data.length);
  }
}