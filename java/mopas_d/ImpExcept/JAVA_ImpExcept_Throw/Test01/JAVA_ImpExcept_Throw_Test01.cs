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
				// ���Ⱦ��� : throw �� ���� new Exception
				new Exception("����");
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
				// ���� : throw ���� �̺�Ʈ �߻�
				throw new Exception("����");
			}
			return nums[index];
		}
		static void Main2() 
		{
			int result = good(3);
		}
	}
}