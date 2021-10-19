/*
Java 클래스 선언
*/

class CMy {
}

public class CMy 
{
}

public interface SDTSendMessage {
}

class CMy extends Element
{
}

//class CMy extends Element1, Element2 /// Java는 다중상속 안됨
//{
//}

class CMy implements IEme1, IEme2		// 다중구현은 됨
{
}

class CMy implements IEme1, 
								IEme2		// 다중구현은 됨
{
}

class CMy extends Elem1 implements IEme1, IEme2
{
}

class CMy implements IEme1, IEme2 extends Elem1
{
}

public class CMy<T> 
{
}

public class CMy<T> extends CP1<T>
{
}

public class CMy<T extends Element>
{
}

public class CMy<T extends Element> extends Ele3
{
}

public class CMy<T extends Element> extends Ele3<T>
{
}

public class CMy<T extends Element> extends Ele3 implements IEme1, IEme2
{
}

public class FileRepository
{
	private final Log	log	= LogFactory.getLog( FileRepository.class );	// 이 문장이 class 영역으로 잡히는 경우 발생했음

	//
}