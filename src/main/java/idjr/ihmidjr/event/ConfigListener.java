package idjr.ihmidjr.event;

import java.util.List;

import idjr.PartieInfo;

public interface ConfigListener {
    void partieValide(String id);

	void partie(List<PartieInfo> partieInfo);
}
