package sample.util;

import sample.action.DeleteAction;

public enum EnumJspName {
	NEW("new.jsp"),
	EDIT("edit.jsp"),
	DETAIL("detail.jsp"),
	LIST("list.jsp"),
	ERROR("error.jsp");
	
	// メンバ変数の定義
	private String name;

	// コンストラクタの実装
	private EnumJspName(String name) {
		this.name = name;
	}

	// メソッドのオーバーライド
	public String toString() {
		return name;
	}
}
