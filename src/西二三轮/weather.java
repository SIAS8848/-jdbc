package 西二三轮;


//ORM思想
public class weather {
	private String id;
	private String fxDate;
	private String textDay;
	private String tempMax;
	private String tempMin;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public weather(String id, String fxDate, String textDay, String tempMax, String tempMin) {
		super();
		this.id = id;
		this.fxDate = fxDate;
		this.textDay = textDay;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
	}

	public String getFxDate() {
		return fxDate;
	}

	public void setFxDate(String fxDate) {
		this.fxDate = fxDate;
	}

	public String getTextDay() {
		return textDay;
	}

	public void setTextday(String textDay) {
		this.textDay = textDay;
	}

	@Override
	public String toString() {
		return "weather [id=" + id + ", fxDate=" + fxDate + ", textDay=" + textDay + ", tempMax=" + tempMax
				+ ", tempMin=" + tempMin + "]";
	}

	public String getTempMax() {
		return tempMax;
	}

	public void setTempMax(String tempMax) {
		this.tempMax = tempMax;
	}

	public String getTempMin() {
		return tempMin;
	}

	public void setTempMin(String tempMin) {
		this.tempMin = tempMin;
	}

	public weather() {
		super();
	}

}
