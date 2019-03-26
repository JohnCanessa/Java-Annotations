import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface FamilyBudget {
	String userRole() default "GUEST";
	int budgetLimit() default 0;
}


class FamilyMember {
	@FamilyBudget(userRole = "SENIOR", budgetLimit = 100)
	public void seniorMember(int budget, int moneySpend) {
		System.out.println("Senior Member");
		System.out.println("Spend: " + moneySpend);
		System.out.println("Budget Left: " + (budget - moneySpend));
	}

	@FamilyBudget(userRole = "JUNIOR", budgetLimit = 50) 
	public void juniorUser(int budget, int moneySpend) {
		System.out.println("Junior Member");
		System.out.println("Spend: " + moneySpend);
		System.out.println("Budget Left: " + (budget - moneySpend));
	}
}


/*
 * 
 */
public class Solution {
	
	/*
	 * 
	 */
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		int testCases = Integer.parseInt(in.nextLine());
		
		// **** ****
		while (testCases > 0) {
			
			// **** get the user role and money spent ****
			String role = in.next();
			int spend   = in.nextInt();
			
			// ???? ????
			System.out.println("role ==>" + role + "<== spend: " + spend);
			
			try {
				
				// **** ****
				Class annotatedClass = FamilyMember.class;
				
				// **** list all methods ****
				Method[] methods 	 = annotatedClass.getMethods();
				
				// **** traverse all methods ****
				for (Method method : methods) {
					
					// **** look for FamilyMember ****
					if (method.isAnnotationPresent(FamilyBudget.class)) {
						
						// ???? ????
						System.out.println("method: " + method);
						
						// **** ****
						FamilyBudget family = method.getAnnotation(FamilyBudget.class);
						
						// ???? ????
						System.out.println("family: " + family);
						
						String userRole = family.userRole();
						int budgetLimit = family.budgetLimit();
						
						// ???? ????
						System.out.println("userRole ==>" + userRole + "<== budgetLimit: " + budgetLimit);
						
						// **** check if the roles match ****
						if (userRole.equals(role)) {
							if(spend <= budgetLimit) {
								method.invoke(FamilyMember.class.newInstance(), budgetLimit, spend);
							}
							else {
								System.out.println("Budget Limit Over");
							}
							
							// ???? ????
							System.out.println();
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			testCases--;
		}
	}
}

