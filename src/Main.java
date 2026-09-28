
static class AnimalThread extends Thread{
    private final String animalName;
    private final int speed;
    private int meters=0;
    private boolean accelerated=false;
    public AnimalThread(String animalName, int animalPriority, int speed){
        this.animalName=animalName;
        this.speed=speed;
        setName(animalName);
        setPriority(animalPriority);
    }
    public int getMeters(){
        return meters;
    }
    public void accelerate(){
        accelerated=true;
    }
    @Override
    public void run(){
        while(meters<100){
            meters++;
            System.out.println(animalName+" - "+meters+" м.");
            try{
                if(accelerated){
                    Thread.sleep(speed/3);
                }
                else {
                    Thread.sleep(speed);
                }
            }
            catch (InterruptedException exception){
                System.out.println(animalName+" остановлен");
                return;
            }
        }
        System.out.println(animalName+" достиг финиша");
    }
}
void main(){
    AnimalThread rabbit= new AnimalThread("Кролик", 8, 100);
    AnimalThread turtle= new AnimalThread("Черепаха", 3, 200);
    rabbit.start();
    turtle.start();
    try{
        Thread.sleep(3000);
        System.out.println(" - - - - ПРОМЕЖУТОЧНЫЙ РЕЗУЛЬТАТ - - - - ");
        System.out.println(rabbit.getName()+" - "+rabbit.getMeters()+" м.");
        System.out.println(turtle.getName()+" - "+turtle.getMeters()+" м.");
        if(rabbit.getMeters() > turtle.getMeters()){
            System.out.println("Черепаха отстает \nМеняем приоритеты...");
            turtle.setPriority(10);
            rabbit.setPriority(1);
            turtle.accelerate();
            System.out.println("Черепаха получила ускорение");
        }
        else {
            System.out.println("Кролик отстает \nМеняем приоритеты...");
            turtle.setPriority(1);
            rabbit.setPriority(10);
            rabbit.accelerate();
            System.out.println("Кролик получил ускорение");
        }
        System.out.println(" - - - - приоритеты изменены - - - -");
        rabbit.join();
        turtle.join();
    }
    catch(InterruptedException exception){
        System.out.println("главный поток был прерван "+ exception.getMessage());
    }
    System.out.println("Кролик преодолел "+rabbit.getMeters()+" м.");
    System.out.println("Черепаха преодолела "+turtle.getMeters()+" м.");
}