package HW7;


public class ExampleTestClass {

    @Test(priority = 1)
    public void doPublic(){
        System.out.println("ExampleTestClass first prority ");
    }

    @Test(priority = 2)
    private void doPrivate(){
        System.out.println("Hided ExampleTestClass second priority");
    }

    @Test(priority = 3)
    protected void doProtected(){
        System.out.println("Protected ExampleTestClass third priority");
    }


    @BeforeSuite
    public void doSomePreparation(){
        System.out.println("All preparations has done");
    }

    @AfterSuite
    public void doSomeFinalization(){
        System.out.println("All finalization  has done");
    }

}
