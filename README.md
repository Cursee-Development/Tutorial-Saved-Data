# Tutorial: Saved Data

This repository shows a simple implementation of Minecraft's SavedData, and synchronizing that SavedData to clients for mods based on MultiLoader-Template. 

---

Here are some relevant tutorials to get started:

[Fabric: Persistent States](https://wiki.fabricmc.net/tutorial:persistent_states)

[Forge: Saved Data](https://docs.minecraftforge.net/en/1.20.x/datastorage/saveddata/)

---

[ItemUseCountData](./common/src/main/java/com/cursee/saved_data/core/data/ItemUseCountData.java) is the actual implementation of SavedData, and data is updated via the player using [UsableItem](./common/src/main/java/com/cursee/saved_data/core/world/item/UsableItem.java). 