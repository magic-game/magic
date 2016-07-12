using UnityEngine;
using System.Collections;
using MagicEngine;

public class DamageAction : MonoBehaviour, SpellAction
{
	private int damage;

	public DamageAction(int damage) {
		this.damage = damage;
	}

	// Use this for initialization
	void Start ()
	{
	
	}
	
	// Update is called once per frame
	void Update ()
	{
	
	}

	public void perform(MagicEntity entity, Spell spell) {
		entity.takeDamage (damage);
		spell.handleEvent (new SpellEvent (SpellEventType.Hurt, entity));
	}
}

