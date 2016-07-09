using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public interface SpellTemplate
{
	List<Link> getLinks();
	float getTimeToLive();
}

