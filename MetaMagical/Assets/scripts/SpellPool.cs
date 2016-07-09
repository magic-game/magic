﻿using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using MagicEngine;

public class SpellPool : MonoBehaviour
{
	List<Spell> spells = new List<Spell>();
	MagicEntity player;

    // Use this for initialization
    void Start()
    {
		//  TODO get player ref
		GameObject p = GameObject.FindWithTag("Player");
		if (p != null) {
			player = p.GetComponent<MagicEntity> ();
		}
			
	}

    // Update is called once per frame
    void Update()
    {
		foreach (Spell spell in spells) {
			spell.Update ();
			if (!spell.IsAlive()) {
				spells.Remove (spell);
			}
		}
    }

	public void CastSpell(SpellTemplate spellTemplate) {
		Spell spell = new Spell(spellTemplate);
		spells.Add (spell);
		spell.handleEvent (new SpellEvent(SpellEventType.Cast, player));
	}
}
