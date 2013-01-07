package com.taobao.metamorphosis.network;

import com.taobao.gecko.core.buffer.IoBuffer;


/**
 * ��ѯ�����Ч��offset ��ʽ�� offset topic group partition offset opaque\r\n
 * 
 * @author boyan
 * @Date 2011-4-21
 * 
 */
public class OffsetCommand extends AbstractRequestCommand {
    static final long serialVersionUID = -1L;
    private final int partition;
    private final String group;
    private final long offset;


    public int getPartition() {
        return this.partition;
    }


    public String getGroup() {
        return this.group;
    }


    public long getOffset() {
        return this.offset;
    }


    public OffsetCommand(final String topic, final String group, final int partition, final long offset,
            final Integer opaque) {
        super(topic, opaque);
        this.group = group;
        this.partition = partition;
        this.offset = offset;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.group == null ? 0 : this.group.hashCode());
        result = prime * result + (int) (this.offset ^ this.offset >>> 32);
        result = prime * result + this.partition;
        return result;
    }


    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final OffsetCommand other = (OffsetCommand) obj;
        if (this.group == null) {
            if (other.group != null) {
                return false;
            }
        }
        else if (!this.group.equals(other.group)) {
            return false;
        }
        if (this.offset != other.offset) {
            return false;
        }
        if (this.partition != other.partition) {
            return false;
        }
        return true;
    }


    @Override
    public IoBuffer encode() {
        final IoBuffer buf =
                IoBuffer.allocate(13 + this.getTopic().length() + this.getGroup().length()
                        + ByteUtils.stringSize(this.partition) + ByteUtils.stringSize(this.offset)
                        + ByteUtils.stringSize(this.getOpaque()));
        ByteUtils.setArguments(buf, MetaEncodeCommand.OFFSET_CMD, this.getTopic(), this.getGroup(),
            this.getPartition(), this.offset, this.getOpaque());
        buf.flip();
        return buf;
    }

}