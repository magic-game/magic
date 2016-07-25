using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class FireBallTemplate : SpellTemplate
{
	public List<Link> links;
	public float ttl = 10.0f;

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
		ImpulseAction createEntityAction2 = new ImpulseAction (48.0f);
		link2.spellAction = createEntityAction2;

		Link link3 = new Link ();
		link3.spellEventType = SpellEventType.Creation;
		AddListenerAction listenAction = new AddListenerAction (SpellEventType.Collision);
		link3.spellAction = listenAction;

		Link link4 = new Link ();
		link4.spellEventType = SpellEventType.Collision;
		DamageAction destroyAction = new DamageAction (1);
		link4.spellAction = destroyAction;

		links.Add (link);
		links.Add (link2);
		links.Add (link3);
		links.Add (link4);
	}

	public List<Link> getLinks() {
		return links;
	}

	public float getTimeToLive() {
		return ttl;
	}
}

