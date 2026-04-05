package in.co.rays.proj4.bean;

public class NoteBean extends BaseBean {

	private long notePayableId;
	private String status;
	private Double totalPrincipal;
	private Double totalInterestPaid;
	private Double totalOutstanding;

	public long getNotePayableId() {
		return notePayableId;
	}

	public void setNotePayableId(long notePayableId) {
		this.notePayableId = notePayableId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getTotalPrincipal() {
		return totalPrincipal;
	}

	public void setTotalPrincipal(Double totalPrincipal) {
		this.totalPrincipal = totalPrincipal;
	}

	public Double getTotalInterestPaid() {
		return totalInterestPaid;
	}

	public void setTotalInterestPaid(Double totalInterestPaid) {
		this.totalInterestPaid = totalInterestPaid;
	}

	public Double getTotalOutstanding() {
		return totalOutstanding;
	}

	public void setTotalOutstanding(Double totalOutstanding) {
		this.totalOutstanding = totalOutstanding;
	}

	@Override
	public String getKey() {
		return status;
	}

	@Override
	public String getValue() {
		return status;
	}
}