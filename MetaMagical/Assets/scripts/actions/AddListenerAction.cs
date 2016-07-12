using UnityEngine;
using System.Collections;
using MagicEngine;
using AssemblyCSharp;

public class AddListenerAction : MonoBehaviour, SpellAction, SpellEventListener {

	private SpellEventType eventType;
	private Spell spell;
	private MagicEntity entity;

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {

	}

	public AddListenerAction(SpellEventType eventType) {
		this.eventType = eventType;
	}

	public void perform(MagicEntity entity, Spell spell) {
		this.spell = spell;
		this.entity = entity;
		entity.AddEventListener (this);
		spell.handleEvent (new SpellEvent (SpellEventType.AddListener, entity));
	}

	public void HandleEvent(SpellEventType type, MagicEntity target) {
		if (type == this.eventType) {
			spell.handleEvent (new SpellEvent(SpellEventType.Collision, target));
		}
	}
}
