using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class LightTemplate : SpellTemplate
{

	public List<Link> links;
	public float ttl = 3.0f;

	// Use this for initialization
	public LightTemplate (GameObject spellBall)
	{
		links = new List<Link> ();
		Link link = new Link ();
		link.spellEventType = SpellEventType.Cast;
		CreateEntityAction createEntityAction = new CreateEntityAction (spellBall);
		link.spellAction = createEntityAction;

		links.Add (link);
	}

	public List<Link> getLinks() {
		return links;
	}

	public float getTimeToLive() {
		return ttl;
	}
}

