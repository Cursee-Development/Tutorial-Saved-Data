# Tutorial: SavedData in Minecraft Mods

This repository demonstrates a simple implementation of Minecraft's `SavedData` system, including how to synchronize that data with clients in mods built on the [MultiLoader-Template](https://github.com/jaredlll08/MultiLoader-Template). It's designed as a lightweight reference for mod developers looking to persist and sync custom data. 

---

### Official Resources

To better understand the `SavedData` system on different mod loaders, check out the following tutorials:

- **Fabric:** [Persistent States (Fabric Wiki)](https://wiki.fabricmc.net/tutorial:persistent_states)
- **Forge:** [Saved Data (Forge Docs)](https://docs.minecraftforge.net/en/1.20.x/datastorage/saveddata/)

---

### Overview

- [`ItemUseCountData`](./common/src/main/java/com/cursee/saved_data/core/data/ItemUseCountData.java)  
  Extends `SavedData`, this is the main focus of the demonstration.

- [`UsableItem`](./common/src/main/java/com/cursee/saved_data/core/world/item/UsableItem.java)  
  A custom item that can update both the global use count on usage and individual player use count on sneak usage.

>Feel free to open an issue or discussion if you have questions, suggestions, or improvements you'd like to see!