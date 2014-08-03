package olectronix.hottie.general.data;

public class ReportModel {
	private long id;
	private double exterior;
	private double interior;
	private double hottie;
	private double wtt;
	private double voltage;
	private double status;
	private String heatingTime;
	private int currentYear;
	private int currentMonth;
	private int currentDay;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getExterior() {
		return exterior;
	}
	public void setExterior(double exterior) {
		this.exterior = exterior;
	}
	public double getInterior() {
		return interior;
	}
	public void setInterior(double interior) {
		this.interior = interior;
	}
	public double getHottie() {
		return hottie;
	}
	public void setHottie(double hottie) {
		this.hottie = hottie;
	}
	public double getWtt() {
		return wtt;
	}
	public void setWtt(double wtt) {
		this.wtt = wtt;
	}
	public double getVoltage() {
		return voltage;
	}
	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}
	public double getStatus() {
		return status;
	}
	public void setStatus(double status) {
		this.status = status;
	}
	public String getHeatingTime() {
		return heatingTime;
	}
	public void setHeatingTime(String heatingTime) {
		this.heatingTime = heatingTime;
	}
	public int getCurrentYear() {
		return currentYear;
	}
	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}
	public int getCurrentMonth() {
		return currentMonth;
	}
	public void setCurrentMonth(int currentMonth) {
		this.currentMonth = currentMonth;
	}
	public int getCurrentDay() {
		return currentDay;
	}
	public void setCurrentDay(int currentDay) {
		this.currentDay = currentDay;
	}

}
