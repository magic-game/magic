using UnityEngine;
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

	public MagicEntity perform(MagicEntity entity) {
		Debug.Log ("doing impulse action");
		Transform transform = entity.getTransform ();
		//transform.Translate(transform.forward * speed * Time.deltaTime);
		Rigidbody rb = entity.getRigidbody ();
		//rb.AddForce (transform.forward * speed);
		rb.AddForce(transform.forward * speed, ForceMode.Impulse);
		return entity;
	}

	public SpellEventType getEvent() {
		return SpellEventType.Impulse;
	}
}

