package speerker.p2p;

import java.io.Serializable;

import peerbase.PeerInfo;

public class FilePart implements Serializable {
	private static final long serialVersionUID = 4587406815268423392L;
	protected String transferID;
	protected PeerInfo requester;
	protected Integer part = 0;
	protected Long partSize;
	protected String fileHash;
	protected byte[] data;

	public FilePart() {
	}

	public FilePart(String fileHash, PeerInfo requester, Integer part,
			Long partSize) {
		this(fileHash, requester, part, partSize, "".getBytes());
	}

	public FilePart(String fileHash, PeerInfo requester, Integer part,
			Long partSize, byte[] data) {
		this.part = part;
		this.requester = requester;
		this.partSize = partSize;
		this.fileHash = fileHash;
		this.data = data;
	}

	public String getTransferID() {
		return this.fileHash;
	}

	public PeerInfo getRequester() {
		return requester;
	}

	public void setRequester(PeerInfo requester) {
		this.requester = requester;
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
