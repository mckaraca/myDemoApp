package rabbitescape.engine.behaviours;

import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.Token.Type.*;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

public class Digging extends Behaviour
{
    private int stepsOfDigging;

    @Override
    public void cancel()
    {
        stepsOfDigging = 0;
    }

    @Override
    public boolean checkTriggered( Rabbit rabbit, World world )
    {
        BehaviourTools t = new BehaviourTools( rabbit, world );
        return t.pickUpToken( dig );
    }

    @Override
    public State newState( BehaviourTools t, boolean triggered )
    {
        if ( !triggered && stepsOfDigging == 0 )
        {
            return null;
        }

        if ( t.rabbit.state == RABBIT_DIGGING )
        {
            stepsOfDigging = 1;
            return RABBIT_DIGGING_2;
        }

        if (
               triggered
            || stepsOfDigging > 0
        )
        {
            if ( t.rabbit.onSlope && t.blockHere() != null )
            {
                stepsOfDigging = 1;
                return RABBIT_DIGGING_ON_SLOPE;
            }
            else if ( t.blockBelow() != null )
            {
                if ( t.blockBelow().material == Block.Material.METAL )
                {
                    stepsOfDigging = 0;
                    return RABBIT_DIGGING_USELESSLY;
                }
                else
                {
                stepsOfDigging = 2;
                return RABBIT_DIGGING;
                }
            }
        }

        --stepsOfDigging;
        return null;
    }

    @Override
    public boolean behave( World world, Rabbit rabbit, State state )
    {
        switch ( state )
        {
            case RABBIT_DIGGING:
            {
		
		//asagiya dogru kazma islemi burada gerceklesiyor
		//dig
                world.changes.removeBlockAt( rabbit.x, rabbit.y + 1 );
		//System.out.println("DIGGGINNNNGGGGGGGGGG111111111");                
		++rabbit.y;
                
                return true;
            }
            case RABBIT_DIGGING_ON_SLOPE:
            {
		//egimi olan bir blogu kazma islemi burada yapiliyor
		//dig
                world.changes.removeBlockAt( rabbit.x, rabbit.y );
                rabbit.onSlope = false;
		//System.out.println("DIGGGINNNNGGGGGGGGGG222222222");
                return true;
            }
            case RABBIT_DIGGING_2:
		//bu kismi tam anlamadim asagi dogru digging yapinca buraya da giriyo
		/*{
		System.out.println("DIGGING2");
		return true;
		}*/
            case RABBIT_DIGGING_USELESSLY:
            /*{
		System.out.println("DIGGING USELESSLY");
		*/            
		    return true;
            
            default:
            {
		//digging yapilmayan durumlar
		//System.out.println("Default");
                return false;
            }
        }
    }

    @Override
    public void saveState( Map<String, String> saveState )
    {
        BehaviourState.addToStateIfGtZero(
            saveState, "Digging.stepsOfDigging", stepsOfDigging );
    }

    @Override
    public void restoreFromState( Map<String, String> saveState )
    {
        stepsOfDigging = BehaviourState.restoreFromState(
            saveState, "Digging.stepsOfDigging", stepsOfDigging );
    }

}
