﻿using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using MagicEngine;

public class Spell : MonoBehaviour {

	public float ttl;
	public List<Link> links;
	public List<SpellEvent> nextEvents;

	// Use this for initialization
	public Spell (SpellTemplate spellTemplate) {
		nextEvents = new List<SpellEvent>();
		ttl = spellTemplate.getTimeToLive();
		links = new List<Link> ();
		links.AddRange (spellTemplate.getLinks());
	}

	// Update is called once per frame
	public void Update () {
		ttl = ttl - Time.deltaTime;
		List <SpellEvent> queueEvents = new List<SpellEvent> ();
		queueEvents.AddRange (nextEvents);
		nextEvents.Clear ();
		foreach (SpellEvent ev in queueEvents) {
			this.processEvent (ev);
		}
	}

	public bool IsAlive() {
		return ttl > 0;
	}

	public void handleEvent(SpellEvent spellEvent) {
		nextEvents.Add (spellEvent);
	}

	private void processEvent(SpellEvent spellEvent) {
		foreach (Link link in links) {
			if (link.spellEventType.Equals(spellEvent.spellEventType)) {
				link.spellAction.perform (spellEvent.magicEntity, this);
			}
		}
	}

}
