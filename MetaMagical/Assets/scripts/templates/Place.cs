using System;
using UnityEngine;

public class Place
{
	public Vector3 position;
	public Quaternion rotation;
	public Vector3 forward;

	public Place ()
	{
		position = Vector3.zero;
		rotation = Quaternion.identity;
		forward = Vector3.one;
	}

	public Place(Transform transform) {
		position = transform.position;
		rotation = transform.rotation;
		forward = transform.forward;
	}
}

