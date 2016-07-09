using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class FireBallTemplate : SpellTemplate
{
	public List<Link> links;
	public float ttl = 100.0f;

	// Use this for initialization
	public FireBallTemplate (GameObject spellBall)
	{
		links = new List<Link> ();
		Link link = new Link ();
		link.spellEventType = SpellEventType.Cast;
		CreateEntityAction createEntityAction = new CreateEntityAction (spellBall);
		link.spellAction = createEntityAction;
		Link link2 = new Link ();
		link2.spellEventType = SpellEventType.Creation;
		ImpulseAction createEntityAction2 = new ImpulseAction (8.0f);
		link2.spellAction = createEntityAction2;
		links.Add (link);
		links.Add (link2);
	}

	public List<Link> getLinks() {
		return links;
	}

	public float getTimeToLive() {
		return ttl;
	}
}

