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
    private boolean IsActive;
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
     * This method allows us to place this block to ...
     * @param x
     * @param y
     * @param z 
     */
    public void setCoords(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    
}
