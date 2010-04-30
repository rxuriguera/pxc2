package speerker.p2p;

import java.io.Serializable;

public class FilePart implements Serializable {
	private static final long serialVersionUID = 4587406815268423392L;
	protected Integer transferID = 0;
	protected Integer part = 0;
	protected Long partSize;
	protected String fileHash;
	protected byte[] data;

	public FilePart() {
	}

	public FilePart(Integer transferID, Integer part, Long partSize,
			String fileHash) {
		this(transferID, part, partSize, fileHash, "".getBytes());
	}

	public FilePart(Integer transferID, Integer part, Long partSize,
			String fileHash, byte[] data) {
		this.transferID = transferID;
		this.part = part;
		this.partSize = partSize;
		this.fileHash = fileHash;
		this.data = data;
	}

	public Integer getTransferID() {
		return transferID;
	}

	public void setTransferID(Integer transferID) {
		this.transferID = transferID;
	}

	public Integer getPart() {
		return part;
	}

	public void setPart(Integer part) {
		this.part = part;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Long getPartSize() {
		return partSize;
	}

	public void setPartSize(Long partSize) {
		this.partSize = partSize;
	}

	public String getFileHash() {
		return fileHash;
	}

	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}

}
