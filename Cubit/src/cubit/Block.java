/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cubit;

/**
 * The Block Class. These Block objects are used to create Chunk
 * objects. 
 * 
 * @author BinaryShredders
 */
public class Block {
    private boolean isActive;
    private BlockType Type;
    private float x, y, z;
    
    public enum BlockType {
        
        BlockType_Grass(0),
        BlockType_Sand(1),
        BlockType_Water(2),
        BlockType_Dirt(3),
        BlockType_Stone(4),
        BlockType_Bedrock(5);
        
        /* BlockType Identifier */
        private int BlockID;
        
        /* BlockType Constructor */
        BlockType(int i) {
            BlockID = i;
        }
        
        /* Getter for BlockType */
        public int GetID() {
            return BlockID;
        }
        
        /* Setter for BlockType */
        public void SetID(int i) {
            BlockID = i;
        }
        
    }
    
    /**
     * Block constructor. 
     * @param desired BlockType
     */
    public Block(BlockType type) {
        Type = type;
    }
    
    /**
     * This method allows us to place this block in the scene.
     * The 3-dimensional coordinate specified will place the 
     * block's center at this location
     * @param x-coordinate
     * @param y-coordinate
     * @param z-coordinate
     */
    public void setCoords(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * This method returns true if this block is active,
     * a.k.a. visible in the scene. Otherwise, this method
     * returns false.
     */
    public boolean isActive() {
        return isActive;
    }
    
    /**
     * Allows us to set whether this block is active and visible on the screen 
     * or not active. Any block that is inactive will not render. True means
     * render the block. False means the block will not be rendered.
     * @param boolean that reflects whether the block is active or inactive
     */
    public void setActive(boolean active) {
        isActive = active;
    }
    
    /**
     * Returns the ID of this block type.
     */
    public int getID() {
        return Type.GetID();
    }
    
}
