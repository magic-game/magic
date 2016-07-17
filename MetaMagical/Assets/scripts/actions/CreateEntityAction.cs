using UnityEngine;
using System.Collections;
using MagicEngine;

public class CreateEntityAction : MonoBehaviour, SpellAction
{

	public GameObject spellBall;

	public CreateEntityAction(GameObject spellBall) {
		this.spellBall = spellBall;
	}

	// Use this for initialization
	void Start ()
	{
		
	}
	
	// Update is called once per frame
	void Update ()
	{
	
	}

	public void perform(MagicEntity magicEntity, Spell spell) {
		Transform transform = magicEntity.getTransform ();
		// TODO use the sclae of the spell ball and magic entity 
		GameObject obj = (GameObject) Instantiate (spellBall, transform.position + transform.forward*(2.0f), transform.rotation);
		MonoBehaviour[] monos = obj.GetComponents<MonoBehaviour> ();
		BallFabScript script = obj.GetComponent<BallFabScript> ();
		script.setTimeToLive (spell.ttl);
		spell.handleEvent (new SpellEvent (SpellEventType.Creation, script));
	}
}

