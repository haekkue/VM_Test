using System;

namespace Test
{
	public class ThrowTest2
	{
		static int bad(int index)
		{
			int[] nums = { 300, 600, 900 };
			if (index > nums.Length)
			{
				// 보안약점 : throw 가 없는 new Exception
				new Exception("에러");
			}
			return nums[index];
		}
		static void Main1() 
		{
			int result = bad(3);
		}

		static int good(int index)
		{
			int[] nums = { 300, 600, 900 };
			if (index > nums.Length)
			{
				// 수정 : throw 예외 이벤트 발생
				throw new Exception("에러");
			}
			return nums[index];
		}
		static void Main2() 
		{
			int result = good(3);
		}
	}
}