﻿using UnityEngine;
using System.Collections;
using MagicEngine;

public class ImpulseAction : MonoBehaviour, SpellAction 
{
	float speed;

	public ImpulseAction(float speed) {
		this.speed = speed;
	}

	void Start ()
	{
	
	}

	void Update ()
	{
		
	}

	public void perform(MagicEntity entity, Spell spell) {
		Transform transform = entity.getTransform ();
		Rigidbody rb = entity.getRigidbody ();
		rb.AddForce(transform.forward * speed, ForceMode.Impulse);
		spell.handleEvent (new SpellEvent (SpellEventType.Impulse, entity));
	}
}

