package idjr.ihmidjr.event;

import java.util.List;

import idjr.PartieInfo;

public interface IConfigListener {
	void partieValide(String id);

	void partie(List<PartieInfo> partieInfo);

	void partieValider();
}
