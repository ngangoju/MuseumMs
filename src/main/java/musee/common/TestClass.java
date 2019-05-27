package musee.common;

import javax.inject.Inject;

import com.musee.dao.impl.UserImpl;

public class TestClass {
	
	@Inject
	private static transient UserImpl usersImpl;
	
   public static void main(String ... dddd){

	aa();
}
public static void aa() {
	   
			
}
public static UserImpl getUsersImpl() {
	return usersImpl;
}

public static void setUsersImpl(UserImpl usersImpl) {
	TestClass.usersImpl = usersImpl;
}


}
