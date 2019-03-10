package frc.actions;

public class TimedActionTestAction extends TimedAction{

    public TimedActionTestAction(double timeout) {
        super(timeout);
    }

    @Override
    protected void execute() {
        System.out.println(getTimePassed());
    }
}
