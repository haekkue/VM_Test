// Ŭ���� ���� 5
function Greeter5 (mes5) {
	this.greeting5 = mes5;
    this.greet5 = function() {
		this.fnone = function() {
			onone = "Nice Gory";
            this.one = "Nice Guy";	// �̺�����
			one = "Nice Ggg";		// �̺����� �ٸ� ����, ������ �ܺο��� ȣ���Ҷ��� this.one�� ȣ��� 
            return "My " + this.greeting5 + ", " + onone;  // Greeter1�� teststr�� ���� �ȵ� 
        };
        this.fntwo = function(par1) {
			this.two = "Beautiful Girl " + par1 + ", " + mes5;
            return "Your " + this.one + ", " + onone;
		};
        this.fnthree = function(par1) {
			this.three = "Beautiful Boy " + par1 + ", " + mes5;
            return "There " + this.one + ", " + onone;
		};
		return "Hello 5, " + this.fnone() + ", " + this.one + ", " + this.fntwo("momo") + ", " + this.two + ", " + this.greeting5 + ", " + onone + ", " + one + ", " + this.fnthree("meme") + ", " + this.three ;
	};
	this.fngre = function() {
		return "Hello 5-1, " + this.greeting5 + ", " + onone + ", " + one;
	};
	three = "Three color";
}

	var g5 = new Greeter5('HotHot');
	document.write(g5.greet5());
	document.write("<br>");
	document.write(g5.fngre());
	document.write("<br>");

	document.write("[DIRECT1] " + g5.greeting5 + ", " + g5.one + ", " + g5.two);
	//document.write("[DIRECT1] " + g5.three); �̰� undefined ������� ���� 
	document.write("<br>");