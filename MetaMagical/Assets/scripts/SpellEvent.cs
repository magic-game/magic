using UnityEngine;
using System.Collections;
using MagicEngine;

public class SpellEvent : MonoBehaviour
{

	public SpellEventType spellEventType;
	public MagicEntity magicEntity;

	public SpellEvent(SpellEventType spellEventType, MagicEntity magicEntity) {
		this.spellEventType = spellEventType;
		this.magicEntity = magicEntity;
	}

	// Use this for initialization
	void Start ()
	{
	
	}
	
	// Update is called once per frame
	void Update ()
	{
	
	}
}

