package olectronix.hottie;

public class ReportModel {
	private long id;
	private double exterior;
	private double interior;
	private double hottie;
	private double wtt;
	private double voltage;
	private double status;
	private String heating_time;
	private int current_year;
	private int current_month;
	private int current_day;
	
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
	public String getHeating_time() {
		return heating_time;
	}
	public void setHeating_time(String heating_time) {
		this.heating_time = heating_time;
	}
	public int getCurrent_year() {
		return current_year;
	}
	public void setCurrent_year(int current_year) {
		this.current_year = current_year;
	}
	public int getCurrent_month() {
		return current_month;
	}
	public void setCurrent_month(int current_month) {
		this.current_month = current_month;
	}
	public int getCurrent_day() {
		return current_day;
	}
	public void setCurrent_day(int current_day) {
		this.current_day = current_day;
	}

}
