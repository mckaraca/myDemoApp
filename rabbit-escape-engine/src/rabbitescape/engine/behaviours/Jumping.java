package rabbitescape.engine.behaviours;

import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.Direction.*;
import static rabbitescape.engine.Token.Type.*;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

public class Jumping extends Behaviour{

    boolean hasAbility = false;
    public boolean abilityActive = false;
    private int jumpingCount = 0;
    
    @Override
    public boolean checkTriggered( Rabbit rabbit, World world )
    {
        BehaviourTools t = new BehaviourTools( rabbit, world );

        return !hasAbility && t.pickUpToken( jump, true );
    }

    @Override
    public State newState( BehaviourTools t, boolean triggered )
    {
        if ( triggered )
        {
            hasAbility = true;
        }

        if ( !hasAbility )
        {
            return null;
        }

        switch ( t.rabbit.state )
        {
            case RABBIT_JUMPING_RIGHT_START:
            case RABBIT_JUMPING_LEFT_START:
                //ilk ziplamaya basladigi an
                return newStateStart( t );
            case RABBIT_JUMPING_RIGHT_CONTINUE_1:
            case RABBIT_JUMPING_LEFT_CONTINUE_1:
                //ziplamaya devam ettigi anlar
                return newStateCont1( t );
            case RABBIT_JUMPING_RIGHT_CONTINUE_2:
            case RABBIT_JUMPING_LEFT_CONTINUE_2:
                //ziplamaya devam ettigi anlar
                return newStateCont2( t );
            default:
                //tirmanmadigi butun durumlar
                //System.out.println("DEFAULT");
                return newStateNotJumping( t );
        }
    }
    
    private State newStateStart( BehaviourTools t ){

        if ( jumpingCount < 3 )
        {
            //ziplamaya basladigi blok
            jumpingCount++;
            return t.rl(RABBIT_JUMPING_RIGHT_CONTINUE_1, RABBIT_JUMPING_LEFT_CONTINUE_1);
        }
        else
        {
            //ziplama isleminin bitecegi nokta
            jumpingCount = 0;
            return t.rl(RABBIT_JUMPING_RIGHT_END, RABBIT_JUMPING_LEFT_END);
        }
    }
   
    private State newStateCont1( BehaviourTools t )
    {
        jumpingCount++;
        return t.rl(RABBIT_JUMPING_RIGHT_CONTINUE_2, RABBIT_JUMPING_LEFT_CONTINUE_2);
    }

    private State newStateCont2( BehaviourTools t ){

        if ( jumpingCount < 3 )
        {
            jumpingCount++;
            return t.rl(RABBIT_JUMPING_RIGHT_CONTINUE_2, RABBIT_JUMPING_LEFT_CONTINUE_2);
        }
        else
        {
            jumpingCount = 0;
            return t.rl(RABBIT_JUMPING_RIGHT_END, RABBIT_JUMPING_LEFT_END);
        }
    }

    private State newStateNotJumping( BehaviourTools t )
    {
        if ( jumpingCount >= 3 )
        {
            return t.rl(RABBIT_JUMPING_RIGHT_END, RABBIT_CLIMBING_LEFT_END);
        }
        return null;
    }
    
    @Override
    public boolean behave( World world, Rabbit rabbit, State state )
    {     
        switch ( state )
        {
            case RABBIT_JUMPING_RIGHT_START:
            case RABBIT_JUMPING_LEFT_START:
            {
                abilityActive = true;
                return true;
            }
            case RABBIT_JUMPING_RIGHT_END:
            case RABBIT_JUMPING_LEFT_END:
            {
                abilityActive = false;
                return true;
            }
            case RABBIT_JUMPING_RIGHT_CONTINUE_1:
            case RABBIT_JUMPING_LEFT_CONTINUE_1:
            {
                --rabbit.y;//////////////////////////////////emin degilim
                abilityActive = true;
                return true;
            }
            case RABBIT_JUMPING_RIGHT_CONTINUE_2:
            case RABBIT_JUMPING_LEFT_CONTINUE_2:
            {
                abilityActive = true;
                --rabbit.y;
                return true;
            }
            case RABBIT_JUMPING_RIGHT_BANG_HEAD:
            case RABBIT_JUMPING_LEFT_BANG_HEAD:
            {
                //burayi bilmiyorum
                rabbit.dir = opposite( rabbit.dir );
                return true;
            }
            default:
            {
                return false;
            }
        }
    }

    @Override
    public void cancel(){
        abilityActive = false;
    }
    
    @Override
    public void saveState( Map<String, String> saveState )
    {
        BehaviourState.addToStateIfTrue(
            saveState, "Jumping.hasAbility", hasAbility
        );

        BehaviourState.addToStateIfTrue(
            saveState, "Jumping.abilityActive", abilityActive
        );
    }

    @Override
    public void restoreFromState( Map<String, String> saveState )
    {
        hasAbility = BehaviourState.restoreFromState(
            saveState, "Jumping.hasAbility", hasAbility
        );

        abilityActive = BehaviourState.restoreFromState(
            saveState, "Jumping.abilityActive", abilityActive
        );
    }
}
